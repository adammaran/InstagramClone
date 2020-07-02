const mongoose = require("mongoose");
const moment = require("moment");
const momentTZ = require("moment-timezone");

const { LikeSchema } = require("../models/like_model");
const { CommentSchema } = require("../models/comment_model");

const schema = new mongoose.Schema({
    user_id: {
        type: String,
        required: true
    },
    username: {
        type: String,
        required: true
    },
    avatar: {
        type: Buffer
    },
    timestamp: {
        type: String,
        default: () => moment.tz(moment(), "Europe/Belgrade").format()
    },
    location: {
        type: String
    },
    description: {
        type: String,
        maxlength: 500
    },
    image: {
        type: Buffer,
        required: true
    },
    likes: {
        type: [LikeSchema]
    },
    comments: {
        type: [CommentSchema]
    }
});

const Post = mongoose.model('Post', schema);

exports.Post = Post;