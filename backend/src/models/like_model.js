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
    }
});

const Like = mongoose.model('Like', schema);

exports.Like = Like;
exports.LikeSchema = schema;