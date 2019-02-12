//Write your own contracts here. Currently compiles using solc v0.4.15+commit.bbb8e64f.
pragma solidity ^0.4.25;
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