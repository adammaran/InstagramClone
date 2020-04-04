const express = require("express");
const router = express.Router();
const posts = require("../controllers/posts_controller");
const image = require("../middlewares/imagesMiddleware");
const auth = require("../middlewares/authMiddleware");
const timeZone = require("../middlewares/timeZoneMiddleware");

router.post('/', auth, posts.create);
router.get('/', auth, posts.getAll);
router.post('/create', auth, image.upload.single('image'), posts.create);

module.exports = router;