pragma solidity ^0.4.0;

contract SceneOuverte {
    function SceneOuverte(){

    }

    string[12] passageArtistes;
    uint creneauxLibres = 12;
    uint tour;

    function sInscrire(string nomDArtiste) public {
        if  (creneauxLibres > 0){
            passageArtistes[12-creneauxLibres] = nomDArtiste;
            creneauxLibres--;
        }
    }

    function passerArtisteSuivant() public {
        if (tour + creneauxLibres < 12 ){
            tour++;
        }

    }

    function artisteEnCours() public constant returns (string) {
        if  (tour + creneauxLibres < 12 ){
            return passageArtistes[tour];
        }
        else {
            return "FIN";
        }

    }


}
