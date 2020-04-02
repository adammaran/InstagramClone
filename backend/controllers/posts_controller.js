const moment = require('moment');
const _ = require("lodash");
const Post = require("../models/post_model");

exports.create = async (req, res) => {

};

exports.uploadImage = async (req, res) => {
    try {
        let post = await Post.findById(req.user._id);

        if (!post) {
            return res.status(404).send();
        }

        user.avatar = req.file.buffer;

        await user.save();

        return res.status(201).send(user);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.getAll = async (req, res) => {

};