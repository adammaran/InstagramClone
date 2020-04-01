const mongoose = require("mongoose");
const moment = require("moment");

const likeSchema = new mongoose.Schema({
    user_id: {
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
    user_id: {
        type: String,
        required: true
    },
    timestamp: {
        type: String,
        required: true,
        default: () => moment().format()
    },
    body: {
        type: String,
        required: true,
        maxlength: 500
    }
});

const schema = new mongoose.Schema({
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
    imagePath: {
        type: String,
        required: true
    },
    likes: {
        type: [likeSchema]
    },
    comments: {
        type: [commentSchema]
    }
});

const Post = mongoose.model('Post', schema);

exports.Post = Post;