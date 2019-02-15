pragma solidity ^0.4.0;

contract Cogere {
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
