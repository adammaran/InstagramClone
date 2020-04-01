const express = require("express");
const router = express.Router();
const users = require("../controllers/users_controller");
const auth = require("../middlewares/authMiddleware");
const timeZone = require("../middlewares/timeZoneMiddleware");

router.post('/', users.create);
router.get('/', auth, users.findbyId);


module.exports = router;