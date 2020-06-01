const express = require("express");
const router = express.Router();
const users = require("../controllers/users_controller");
const image = require("../middlewares/imagesMiddleware");
const auth = require("../middlewares/authMiddleware");

router.get('/search/:username', auth, users.findbyUsername);
router.get('/', auth, users.getCurrentUser);
router.post('/', users.create);
router.patch('/edit', auth, users.edit);
router.patch('/toggle/private', auth, users.togglePrivate);
router.patch('/toggle/active', auth, users.toggleActive);
router.post('/avatar', auth, image.upload.single('avatar'), users.uploadAvatar);
router.patch('/follow/:id', auth, users.follow);
router.patch('/unfollow/:id', auth, users.unfollow);
router.get('/stats/:id?', auth, users.getUserStats);
router.get('/followers/:id?', auth, users.getFollowerList);
router.get('/following/:id?', auth, users.getFollowingList);

module.exports = router;