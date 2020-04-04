const moment = require('moment');
const _ = require("lodash");
const { Post } = require("../models/post_model");

exports.create = async (req, res) => {
    try {
        let post = new Post(req.body);
        post.user_id = req.user._id;
        post.image = req.file.buffer;

        await post.save();

        return res.status(201).send(post);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.getAll = async (req, res) => {
    try {
        const posts = await Post.find({ user_id: req.user._id });
        
        if (!posts) {
            return res.status(404).send('No posts found');
        }

        res.status(200).send(posts);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};