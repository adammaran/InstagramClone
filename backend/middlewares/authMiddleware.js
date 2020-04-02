const jwt = require("jsonwebtoken");
const config = require("config");

module.exports = async (req, res, next) => {
  const token = req.header('Authorization').split(' ')[1];
  
  if (!token) {
    return res.status(401).send('Access denied.');
  }

  try {
    const decodedToken = await jwt.verify(token, config.get('jwtPrivateKey'));
    req.user = decodedToken;
    next();
  } catch (ex) {
    res.status(400).send('Bad request');
    console.log(ex.message);
  }
};