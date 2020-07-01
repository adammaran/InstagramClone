const moment = require("moment");

const { User } = require("../models/user_model");
const { Post } = require("../models/post_model");
const { Like } = require("../models/like_model");
const { Comment } = require("../models/comment_model");

exports.create = async (req, res) => {
    try {
        const user = await User.findById(req.user._id);
        let post = new Post(req.body);

        post.user_id = user._id;
        post.username = user.username;

        if (user.avatar)
            post.avatar = user.avatar;
            
        post.image = req.file.buffer;

        await post.save();

        return res.status(201).send(post);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.edit = async (req, res) => {
    try {
        const post = await Post.findById(req.params.post_id);

        if (req.body.location) 
            post.location = req.body.location;
        
        if (req.body.description) 
            post.description = req.body.description;

        await post.save();

        return res.status(200).send(post);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.deletePost = async (req, res) => {
    try {
        await Post.findByIdAndDelete(req.params.post_id);

        return res.status(200).send();
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
        const itemsPerPage = 10;
        const page = req.params.page;
        const range = itemsPerPage * page - itemsPerPage;
        const user = await User.findById(req.user._id);
        let posts = await Post.find();

        posts = posts.filter(post => user.following.includes(post.user_id) || user._id.equals(post.user_id));

        posts.sort((a, b) => (moment(a.timestamp).isBefore(b.timestamp)) ? 1 : ((moment(b.timestamp).isBefore(a.timestamp)) ? -1 : 0));

        let feed = posts.slice(range, range + itemsPerPage);
        feed = JSON.stringify(feed);

        res.status(200).send(feed);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};

exports.getExplore = async (req, res) => {
    try {
        const itemsPerPage = 12;
        const page = req.params.page;
        const range = itemsPerPage * page - itemsPerPage;
        const user = await User.findById(req.user._id);
        let posts = await Post.find();

        posts = posts.filter(post => !user.following.includes(post.user_id) && !user._id.equals(post.user_id));

        posts.sort((a, b) => (moment(a.timestamp).isBefore(b.timestamp)) ? 1 : ((moment(b.timestamp).isBefore(a.timestamp)) ? -1 : 0));

        let feed = posts.slice(range, range + itemsPerPage);
        feed = JSON.stringify(feed);

        res.status(200).send(feed);
    } catch (ex) {
        console.log(ex);
        return res.status(500).send(ex.message);
    }
};