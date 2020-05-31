const { User } = require("../models/user_model");
const bcrypt = require("bcryptjs");
const Joi = require("@hapi/joi");

const validate = (body) => {
  const schema = Joi.object({
    email: Joi.string().min(4).max(225).required().email(),
    password: Joi.string().min(6).max(225).required(),
  });

  return schema.validate(body);
}

exports.login = async (req, res) => {
  const { error } = validate(req.body);
  if (error) {
    return res.status(400).send(error.details[0].message);
  }

  let user = null;

  try {
    user = await User.findOne({ email: req.body.email });
  } catch(ex) {
    console.log(ex);
    return res.status(500).send();
  }

  if (!user) {
    return res.status(400).send('Pogrešan email ili lozinka.');
  }

  if (!user.isActive)
    user.isActive = true;
  
  const isPasswordValid = await bcrypt.compare(req.body.password, user.password);
  if (!isPasswordValid) {
    return res.status(400).send('Pogrešna lozinka.');
  }

  const token = {
    token: user.generateAccessToken()
  };

  res.send(token);
};