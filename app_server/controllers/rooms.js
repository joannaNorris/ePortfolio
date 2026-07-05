const rooms = (req, res) => {
    res.render('rooms', {
        title: 'Rooms at Travlr Getaways',
        isRooms: true
    });
};

module.exports = {
    rooms
};