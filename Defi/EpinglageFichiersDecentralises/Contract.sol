pragma solidity ^0.5.5;

contract epinglage {
    
    string[] private epingles;
    address payable private tresorier;
    
    event Epingle(string hash);
    
    constructor(address payable _tresorier) public {
        tresorier = _tresorier;
    }
    
    function changeTresorier(address payable _tresorier) public {
        require(msg.sender == tresorier);
        tresorier = _tresorier;
    }
    
    function payerStockage(string memory hash) public payable {
        require(msg.value > 0.1 ether, "Stockage supérieur à 0.1 ether.");
        tresorier.transfer(msg.value);
        epingles.push(hash);
        emit Epingle(hash);
    }
    
}
