const express = require("express");
const router = express.Router();
const posts = require("../controllers/posts_controller");
const image = require("../middlewares/imagesMiddleware");
const auth = require("../middlewares/authMiddleware");
const timeZone = require("../middlewares/timeZoneMiddleware");

router.post('/', auth, posts.create);
router.get('/', posts.getAll);
router.post('/image', auth, image.upload.single('image'), posts.uploadImage);

module.exports = router;