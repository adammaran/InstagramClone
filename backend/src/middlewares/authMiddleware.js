const jwt = require("jsonwebtoken");
const config = require("config");

module.exports = async (req, res, next) => {
  try {
    const token = req.header('Authorization').replace('Bearer ', '');
    
    if (!token) {
      throw new Error();
    }

    const decodedToken = await jwt.verify(token, config.get('jwtPrivateKey'));
    req.user = decodedToken;
    next();
  } catch (ex) {
    res.status(401).send('Access denied.');
    console.log(ex.message);
  }
};