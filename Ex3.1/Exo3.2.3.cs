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
    if ( SafeMath.add(tour, creneauxLibres) < 12 ){
      tour++;
    }
    
  }

  function artisteEnCours() public view returns (string) {
    if  (SafeMath.add(tour, creneauxLibres) < 12 ){
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

contract Cogere {

  mapping (address => uint) organisateurs;

   constructor() public {
    organisateurs[msg.sender] = 100;
  }

  function transferOrga(address orga, uint parts) public {
    uint transfertParts = SafeMath.div(SafeMath.mul(parts, organisateurs[msg.sender]), 100);
    organisateurs[msg.sender] -= transfertParts;
    organisateurs[orga] = transfertParts; 
  }

  function estOrga(address orga) public view returns (bool) {
    if  (organisateurs[orga] != 0) return true;
  }
  
}
contract CagnotteFestivale is Cogere {  
  
  mapping (address => bool) festivaliers;
  uint private depensesTotales;
  string[] private sponsors;
  uint private nombrePlaces;

  constructor() public {
      nombrePlaces = 500;
  }

  function acheterTicket() public payable {
    require(nombrePlaces > 0);
    require(msg.value >= 500 finney, "Place à 0,5 Ethers");
    festivaliers[msg.sender] = true;
    nombrePlaces--;
  }

  function payer(address destinataire, uint montant) public { //payable ne fonctionne pas av 0.5.0
    require(estOrga(msg.sender));
    require(destinataire != address(0));
    require(montant>0);
    destinataire.transfer(montant);// l'absence de payable n'empêche pas l'utulisation de la fonction transfer()
  }

  function comptabiliserDepense(uint montant) private {
    depensesTotales = SafeMath.add(depensesTotales, montant);
  }

  function sponsoriser(string memory nom) public payable{
    require(msg.value >= 30 ether);
    sponsors.push(nom);
  }

}

/**
 * @title SafeMath
 * @dev Unsigned math operations with safety checks that revert on error
 */
library SafeMath {
    /**
     * @dev Multiplies two unsigned integers, reverts on overflow.
     */
    function mul(uint256 a, uint256 b) internal pure returns (uint256) {
        // Gas optimization: this is cheaper than requiring 'a' not being zero, but the
        // benefit is lost if 'b' is also tested.
        // See: https://github.com/OpenZeppelin/openzeppelin-solidity/pull/522
        if (a == 0) {
            return 0;
        }

        uint256 c = a * b;
        require(c / a == b);

        return c;
    }

    /**
     * @dev Integer division of two unsigned integers truncating the quotient, reverts on division by zero.
     */
    function div(uint256 a, uint256 b) internal pure returns (uint256) {
        // Solidity only automatically asserts when dividing by 0
        require(b > 0);
        uint256 c = a / b;
        // assert(a == b * c + a % b); // There is no case in which this doesn't hold

        return c;
    }

    /**
     * @dev Subtracts two unsigned integers, reverts on overflow (i.e. if subtrahend is greater than minuend).
     */
    function sub(uint256 a, uint256 b) internal pure returns (uint256) {
        require(b <= a);
        uint256 c = a - b;

        return c;
    }

    /**
     * @dev Adds two unsigned integers, reverts on overflow.
     */
    function add(uint256 a, uint256 b) internal pure returns (uint256) {
        uint256 c = a + b;
        require(c >= a);

        return c;
    }

    /**
     * @dev Divides two unsigned integers and returns the remainder (unsigned integer modulo),
     * reverts when dividing by zero.
     */
    function mod(uint256 a, uint256 b) internal pure returns (uint256) {
        require(b != 0);
        return a % b;
    }
}
