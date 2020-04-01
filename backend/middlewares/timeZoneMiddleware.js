module.exports = (req, res, next) => {
    const timeZone = req.header('x-time-zone');
  
    req.timeZone = timeZone;
    next();
  };