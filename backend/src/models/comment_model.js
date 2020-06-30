const mongoose = require("mongoose");
const moment = require("moment");

const schema = new mongoose.Schema({
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

const Comment = mongoose.model('Comment', schema);

exports.Comment = Comment;
exports.CommentSchema = schema;