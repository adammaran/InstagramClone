const moment = require('moment');
const _ = require("lodash");
const { Post, Like, Comment } = require("../models/post_model");
const { User } = require("../models/user_model");

exports.create = async (req, res) => {
    try {
        const user = await User.findById(req.user._id);
        let post = new Post(req.body);

        post.user_id = user._id;
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
        const post = await Post.findById(req.params.post_id);
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

exports.unlike = async (req, res) => {
    try {
        const user = await User.findById(req.user._id).select('username');
        const post = await Post.findById(req.params.post_id);

        const likeIndex = post.likes.findIndex(like => like.username === user.username);

        if (likeIndex === -1)
            return res.status(400).send('Not liked');

        post.likes.splice(likeIndex, 1);

        await post.save();

        return res.status(200).send(post);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.comment = async (req, res) => {
    try {
        const user = await User.findById(req.user._id).select('username');
        const post = await Post.findById(req.params.post_id);

        const comment = new Comment({ username: user.username, comment: req.body.comment });

        post.comments.push(comment);

        await post.save();

        return res.status(201).send(post);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.deleteComment = async (req, res) => {
    try {
        const post = await Post.findById(req.params.post_id);
        const commentId = req.body.comment_id;

        const commentIndex = post.comments.findIndex(comment => comment._id === commentId);

        if (commentIndex === -1)
            return res.status(400).send('Comment not found');

        post.comments.splice(commentIndex, 1);

        await post.save();

        return res.status(200).send(post);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.getFeed = async (req, res) => {
    try {
        const itemsPerPage = 20;
        const page = req.params.page;
        const range = itemsPerPage * page - itemsPerPage;
        const user = await User.findById(req.user._id);
        const posts = [];

        user.following.forEach(user => posts.concat(user.posts));

        posts.sort((a, b) => (a.timestamp > b.timestamp) ? 1 : ((b.timestamp > a.timestamp) ? -1 : 0));

        const feed = posts.slice(range, range + itemsPerPage);

        res.status(200).send(feed);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};