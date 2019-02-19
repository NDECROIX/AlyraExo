pragma solidity ^0.5.3;
contract Pulsation {

  uint public battement;
  string private message;

  constructor(string memory _message) public {
    battement = 0;
    message = _message;
  }

  function ajouterBattement() public returns (string memory) {
      battement++;
      return message;
  }
  

}

contract Pendule is Pulsation("tic"){
    
    Pulsation internal tac;
    string[2] public balancier;
    
    function provoquerDesPulsations() public {
        ajouterBattement();
    }
    
    function ajouterTac(Pulsation t) public { tac = t;}
    
    function mouvementsBalancier() public {
         balancier[0] = ajouterBattement();
         balancier[1] = tac.ajouterBattement();
    }

    
}