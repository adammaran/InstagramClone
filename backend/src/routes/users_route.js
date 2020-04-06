const express = require("express");
const router = express.Router();
const users = require("../controllers/users_controller");
const image = require("../middlewares/imagesMiddleware");
const auth = require("../middlewares/authMiddleware");
const timeZone = require("../middlewares/timeZoneMiddleware");

router.get('/:username', auth, users.findbyUsername);
router.post('/', users.create);
router.post('/avatar', auth, image.upload.single('avatar'), users.uploadAvatar);
router.patch('/follow/:id', auth, users.follow);
router.patch('/unfollow/:id', auth, users.unfollow);

module.exports = router;