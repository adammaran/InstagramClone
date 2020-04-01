const mongoose = require("mongoose");

module.exports = function () {
    mongoose.connect('mongodb://localhost:27017/instagram', {
        useNewUrlParser: true,
        useCreateIndex: true,
        useUnifiedTopology: true 
    }).then(() => console.log('connected to mongodb...'));
};
