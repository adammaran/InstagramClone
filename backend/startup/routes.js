const express = require("express");
const users = require('../routes/users_route');
const auth = require('../routes/auth_route');

module.exports = function(app) {
  // Parse incoming request bodies middleware
  app.use(express.json());

  app.use('/api/users', users);
  app.use('/api/auth', auth);
};