contract Assemblee {

  string nomAssemble;
  address[] membres;

  constructor(string nom) public{
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
}