const express = require("express");
const router = express.Router();
const posts = require("../controllers/posts_controller");
const image = require("../middlewares/imagesMiddleware");
const auth = require("../middlewares/authMiddleware");
const timeZone = require("../middlewares/timeZoneMiddleware");

router.get('/', auth, posts.getAll);
router.post('/create', auth, image.upload.single('image'), posts.create);
router.post('/like', auth, posts.like);
router.post('/comment', auth, posts.comment);

module.exports = router;