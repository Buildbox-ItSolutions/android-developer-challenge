package br.com.codigozeroum.androiddeveloperchallenge.models

class SearchObjectResponse(
    var Search: List<Result>,
    var totalResults: String,
    var Response: String,
    var Error: String
)

class Result(
    var Title: String,
    var Year: String,
    var imdbID: String,
    var Type: String,
    var Poster: String
)

class ResultIMDbId(
    var Title: String,
    var Year: String,
    var Rated: String,
    var Released: String,
    var Runtime: String,
    var Genre: String,
    var Director: String,
    var Writer: String,
    var Actors: String,
    var Plot: String,
    var Language: String,
    var Country: String,
    var Poster: String,
    var imdbRating: String,
    var imdbVotes: String,
    var imdbID: String,
    var Type: String,
    var Production: String,
    var Response: String,
    var Error: String
)