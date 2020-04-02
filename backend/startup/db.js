const mongoose = require("mongoose");
const config = require("config");

module.exports = function () {
    mongoose.connect(config.get('mongodb_url'), {
        useNewUrlParser: true,
        useCreateIndex: true,
        useUnifiedTopology: true 
    }).then(() => console.log('connected to mongodb...'));
};
