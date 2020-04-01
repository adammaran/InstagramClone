const express = require("express");
const router = express.Router();
const authController = require("../controllers/auth_controller");

router.post('/', authController.login);

module.exports = router;