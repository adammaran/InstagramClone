const app = require('express')();
const http = require('http').Server(app);

// Connect mongodb
require("../src/startup/db")();

// Load routes
require("../src/startup/routes")(app);
require("../src/startup/config")();

// Start express server
const port = process.env.PORT || 3000;
http.listen(port, () => console.log(`Listening on port ${port}...`));