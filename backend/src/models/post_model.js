const mongoose = require("mongoose");
const moment = require("moment");

const likeSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true
    },
    timestamp: {
        type: String,
        required: true,
        default: () => moment().format()
    }
});

const commentSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true
    },
    timestamp: {
        type: String,
        required: true,
        default: () => moment().format()
    },
    comment: {
        type: String,
        required: true,
        maxlength: 500
    }
});

const postSchema = new mongoose.Schema({
    user_id: {
        type: String,
        required: true
    },
    timestamp: {
        type: String,
        default: () => moment().format()
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
        type: [likeSchema]
    },
    comments: {
        type: [commentSchema]
    }
});

const Post = mongoose.model('Post', postSchema);
const Like = mongoose.model('Like', likeSchema);
const Comment = mongoose.model('Comment', commentSchema);

exports.Post = Post;
exports.Like = Like;
exports.Comment = Comment;