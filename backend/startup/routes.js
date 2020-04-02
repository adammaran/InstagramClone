const express = require("express");
const auth = require('../routes/auth_route');
const users = require('../routes/users_route');
const posts = require('../routes/posts_route');

module.exports = function(app) {
  // Parse incoming request bodies middleware
  app.use(express.json());

  app.use('/api/auth', auth);
  app.use('/api/users', users);
  app.use('/api/posts', posts);
};