const express = require("express");
const router = express.Router();
const users = require("../controllers/users_controller");
const image = require("../middlewares/imagesMiddleware");
const auth = require("../middlewares/authMiddleware");
const timeZone = require("../middlewares/timeZoneMiddleware");

router.post('/', users.create);
router.get('/:id', auth, users.findbyId);
router.post('/avatar', auth, image.upload.single('avatar'), users.uploadAvatar);

module.exports = router;