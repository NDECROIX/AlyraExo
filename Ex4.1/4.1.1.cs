pragma solidity ^0.5.2;


import "github.com/OpenZeppelin/openzeppelin-solidity/contracts/math/SafeMath.sol";


contract Credibilite {


   using SafeMath for uint256;

   mapping (address => uint256) public cred;

   bytes32[] private devoirs;
   
   uint private addCred;
   
   constructor() public {
       addCred = 30;
   }
   
   
   function produireHash(string memory url) public pure returns (bytes32){
       bytes32 urlB;
       
       assembly { urlB := mload(add(url, 32)) }
       
       return keccak256(abi.encodePacked(urlB));
       
   }
   
   function transfer(address destinataire, uint256 valeur) public {
       require(cred[msg.sender] > (valeur)); // il doit avoir un cred de plus que la valeur.
       require(cred[destinataire] != 0);
       cred[msg.sender] -= valeur;
       cred[destinataire] += valeur;
   }
   
   function remettre(bytes32 dev) public returns (uint){
       devoirs.push(dev);
       cred[msg.sender] += addCred;
       
       if(addCred > 10) addCred -= 10;
       
       return devoirs.length;
       
   }
   
}


