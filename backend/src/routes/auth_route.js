const express = require("express");
const router = express.Router();
const auth = require("../middlewares/authMiddleware");
const authController = require("../controllers/auth_controller");

router.post('/login', authController.login);
router.post('/logout', auth, (req, res) => {
    res.status(200).send();
});

module.exports = router;