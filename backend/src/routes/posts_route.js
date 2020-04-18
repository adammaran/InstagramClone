const express = require("express");
const router = express.Router();
const posts = require("../controllers/posts_controller");
const image = require("../middlewares/imagesMiddleware");
const auth = require("../middlewares/authMiddleware");
const timeZone = require("../middlewares/timeZoneMiddleware");

router.get('/feed/:page', auth, posts.getFeed);
router.post('/create', auth, image.upload.single('image'), posts.create);
router.patch('/like/:post_id', auth, posts.like);
router.patch('/unlike/:post_id', auth, posts.unlike);
router.patch('/comment/:post_id', auth, posts.comment);
router.patch('/delete-comment/:post_id', auth, posts.deleteComment);

module.exports = router;