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

contract Pendule is Pulsation("dong"){
    
    Pulsation[2] public tictac;
    
    string[] private balancier;
    
    constructor() public {
        tictac[0] = new Pulsation("Tic");
        tictac[1] = new Pulsation("Tac");
    }
    
    function mouvementsBalancier(uint k) public {
        
        for (uint i = 0; i < k ; i += 2){
            balancier.push(tictac[0].ajouterBattement());
            if(i + 2 <= k ) balancier.push(tictac[1].ajouterBattement());
        }
    }
}