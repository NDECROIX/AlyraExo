pragma solidity ^0.4.18;
contract Pulsation {

  uint public battement;

  constructor() public {
    battement = 0;
  }

  function ajouterBattement() public {
    battement++;
  }

}