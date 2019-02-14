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
  
  address[] participants;

  string[] public descriptionDecisions;
  uint[] public votesPour;
  uint[] public votesContre;

  function participer() public {
    if (estMembre(msg.sender)) participants.push(msg.sender); 
  }

  function estParticipant(address utilisateur) public view returns (bool){
    for(uint i = 0; i < participants.length; i++){
      if (participants[i] == utilisateur) return true;
    }
  }

  function proposerDecision(string description) public {
    if (estParticipant(msg.sender)){
      descriptionDecisions.push(description);
      votesPour.push(0);
      votesContre.push(0);
    }
  }

  function voter(uint proposition, uint pourContre) public {
      if (pourContre == 1) votesPour[proposition]++;
      else votesContre[proposition]++;
  }
  
}