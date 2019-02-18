pragma solidity ^0.4.25;
contract CagnotteFestivale is Cogere {  
  
  mapping (address => bool) festivaliers;
  uint private depensesTotales;
  string[] private sponsors; // liste des sponsors
  uint private nombrePlaces; 

  constructor() public {
      nombrePlaces = 500; // on init le nombre de places
  }
  
  function () external payable{}

  function acheterTicket() public payable {
    require(nombrePlaces > 0); // l'on control qu'il reste des places
    require(msg.value >= 500 finney, "Place à 0,5 Ethers");
    festivaliers[msg.sender] = true;
    nombrePlaces--;
  }

  function payer(address destinataire, uint montant) public { //type address payable ne fonctionne pas av 0.5.0
    require(estOrga(msg.sender));
    require(destinataire != address(0));
    require(montant>0);
    destinataire.transfer(montant);// l'absence de payable n'empêche pas l'utulisation de la fonction transfer()
  }

  function comptabiliserDepense(uint montant) private {
    depensesTotales += montant;
  }

  function sponsoriser(string memory nom) public payable{
    require(msg.value >= 30 ether); // minimum de 30 ethers
    sponsors.push(nom); // ajoute le nom aux sponsors
  }
  
  

}