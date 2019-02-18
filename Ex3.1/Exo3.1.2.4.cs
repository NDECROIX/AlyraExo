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
  
  struct Decision {
    string descriptionDecision;
    uint votesPour;
    uint votesContre;
    mapping (address => bool) aVote; 
  }

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
    decisions.push(Decision({descriptionDecision : description, votesPour : 0, votesContre : 0}));    
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

}
