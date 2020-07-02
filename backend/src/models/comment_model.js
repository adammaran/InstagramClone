const mongoose = require("mongoose");
const moment = require("moment");
const momentTZ = require("moment-timezone");

const schema = new mongoose.Schema({
    username: {
        type: String,
        required: true
    },
    timestamp: {
        type: String,
        required: true,
        default: () => moment.tz(moment(), "Europe/Belgrade").format()
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