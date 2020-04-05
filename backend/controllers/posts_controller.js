const moment = require('moment');
const _ = require("lodash");
const { Post, Like, Comment } = require("../models/post_model");
const { User } = require("../models/user_model");

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

exports.like = async (req, res) => {
    try {
        const user = await User.findById(req.user._id).select('username');
        const post = await Post.findById(req.body.post_id);
        let liked = false;

        post.likes.forEach(like => {
            if (like.username === user.username)
                liked = true;
        });

        if (liked)
            return res.status(400).send('Already liked');

        const like = new Like({ username: user.username });

        post.likes.push(like);

        await post.save();

        return res.status(201).send(post);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.comment = async (req, res) => {
    try {
        const user = await User.findById(req.user._id).select('username');
        const post = await Post.findById(req.body.post_id);

        const comment = new Comment({ username: user.username, comment: req.body.comment });

        post.comments.push(comment);

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