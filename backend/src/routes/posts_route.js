const express = require("express");
const router = express.Router();
const posts = require("../controllers/posts_controller");
const image = require("../middlewares/imagesMiddleware");
const auth = require("../middlewares/authMiddleware");

router.get('/feed/:page', auth, posts.getFeed);
router.get('/explore/:page', auth, posts.getExplore);
router.post('/create', auth, image.upload.single('image'), posts.create);
router.delete('/delete/:post_id', auth, posts.deletePost);
router.patch('/edit/:post_id', auth, posts.edit);
router.patch('/like/:post_id', auth, posts.like);
router.patch('/unlike/:post_id', auth, posts.unlike);
router.get('/likes/:post_id', auth, posts.getLikesCount);
router.patch('/comment/:post_id', auth, posts.comment);
router.patch('/delete-comment/:post_id', auth, posts.deleteComment);

module.exports = router;