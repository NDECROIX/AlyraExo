//Write your own contracts here. Currently compiles using solc v0.4.15+commit.bbb8e64f.
pragma solidity ^0.4.25;
contract Assemblee {

  address[] membres;

  function rejoindre() public {
    membres.push(msg.sender); 
  }

  function estMembre(address utilisateur) public view returns (bool){
    for(uint i = 0; i < membres.length; i++){
      if (membres[i] == utilisateur) return true;
    }
  }
}