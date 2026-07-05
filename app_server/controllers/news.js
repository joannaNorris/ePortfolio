const news = (req, res) => {
    res.render('news', {
        title: 'News',
        isNews: true
    });
}

module.exports = {
    news
};