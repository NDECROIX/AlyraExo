//Write your own contracts here. Currently compiles using solc v0.4.15+commit.bbb8e64f.
pragma solidity ^0.4.25;
contract SceneOuverte {

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

contract Assemblee {

  struct Decision {
    string descriptionDecision;
    uint votesPour;
    uint votesContre;
    mapping (address => bool) aVote;
  }

  address[] administrateurs;
  string nomAssemble;
  address[] membres;

  constructor(string nom) public{
    administrateurs.push(msg.sender);
    nomAssemble = nom;
  }

  function rejoindre() public {
    membres.push(msg.sender); 
  }

  function estMembre(address utilisateur) public view returns (bool){
    for(uint i = 0; i < membres.length; i++){
      if (membres[i] == utilisateur) return true;
    }
  }

  function estAdministrateur(address administrateur) public view returns (bool){
    for(uint i = 0; i < administrateurs.length; i++){
      if (administrateurs[i] == administrateur) return true;
    }
  }

  function nomeAdministrateur(address administrateur) public {
    require(estAdministrateur(msg.sender));
    administrateurs.push(administrateur);
  }

  function demissionAdministrateur() public {
    require(estAdministrateur(msg.sender));
    for (uint i = 0; i < administrateurs.length; i++){
      if  (administrateurs[i] == msg.sender){
            delete administrateurs[i];
            break;
      } 
    }
  }

  //Décisions collectives

  Decision[] decisions;
  address[] participants;

  function participer() public {
    require(estMembre(msg.sender), "Il faut être membre de l'assemblée!");
    participants.push(msg.sender); 
  }

  function estParticipant(address utilisateur) public view returns (bool){
    for(uint i = 0; i < participants.length; i++){
      if (participants[i] == utilisateur) return true;
    }
  }

  function proposerDecision(string description) public {
    require(estParticipant(msg.sender), "Il faut être participant!"); 
    Decision memory decision;
    decision.descriptionDecision = description;
    decision.votesPour = 0;
    decision.votesContre = 0;
    decisions.push(decision);    
  }

  function voter(uint proposition, uint pourContre) public {
    require(decisions[proposition].aVote[msg.sender] == false);
    if (pourContre == 1) decisions[proposition].votesPour++;
    else decisions[proposition].votesContre++;
    decisions[proposition].aVote[msg.sender] = true;

  }

  function comptabiliser(uint indice) public view returns (int){
    return int(decisions[indice].votesPour - decisions[indice].votesContre);
  } 

  function fermeePropositionDecision(uint indice) public {
    require(estAdministrateur(msg.sender), "Il faut être administrateur!");
    delete decisions[indice];
  }
}

contract CagnotteFestivale {
  
  mapping (address => uint) organisateurs;

  constructor() public {
    organisateurs[msg.sender] = 100;
  }

  function transferOrga(address orga, uint parts) public {
    uint transfertParts = parts * organisateurs[msg.sender] / 100;
    organisateurs[msg.sender] -= transfertParts;
    organisateurs[orga] = transfertParts; 
  }

  function estOrga(address orga) public view returns (bool) {
    if  (organisateurs[orga] != 0) return true;
  }
}
