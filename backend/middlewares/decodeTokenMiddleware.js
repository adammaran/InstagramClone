const jwt = require("jsonwebtoken");
const config = require("config");

module.exports = (req, res, next) => {
  const token = req.header('x-auth-token');
  
  if (!token) {
    return next();
  }
  
  try {
    const decodedToken = jwt.verify(token, config.get('jwtPrivateKey'));
    req.user = decodedToken;
    next();
  } catch(ex) {
    res.status(400).send('Bad request');
    console.log(ex.message);
  }
};