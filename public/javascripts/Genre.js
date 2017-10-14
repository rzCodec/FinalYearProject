var Genres =
    {
        genres: getGenres(),

        getGenres: function ()
        {
            $.ajax({
                type: "GET",
                url: "https://eternalvibes.me/getGenres",
                cache: false,
                dataType: "jsonp",
                success: function (data){
                    console.log(data);
                    genres = data;
                },
                error: function (data) {
                    console.log("error");
                    genres = data;
                }})
                .then(function(){
                    return genres;
                });
        },
        addGenre: function( genre)
        {
            $.post("https://www.eternalvibes.me/addGenre",
                {
                    genreName: genre
                },
                function (data) {
                    console.log("Data: " + data);
                    if(data == 200)
                    {

                    }
                });
        }
    }