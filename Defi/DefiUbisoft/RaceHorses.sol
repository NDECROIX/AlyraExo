pragma solidity ^0.5.1;

/**
* @title ERC721Simple
*/
contract ERC721horse {
    
    event Transfer(address indexed _from, address indexed _to, uint256 _tokenId);
    event Approval(address indexed _owner, address indexed _approved, uint256 _tokenId);
    
    function balanceOf(address _owner) public view returns (uint256 _balance);
    function ownerOf(uint256 _tokenId) public view returns (address _owner);
    function transferFrom(address _to, uint256 _tokenId) public;
    function approve(address _to, uint256 _tokenId) public;
    function takeOwnership(uint256 _tokenId) public;
 
}
 
/**
* @title ObjetsMagiques
*/   
contract RaceHorses {
    
    struct Horse {
        address owner;
        string name;
        string lastName;
        uint id;
        uint win;
        uint loose;
    }
    
    Horse[] horses;
    
    mapping (address => uint) ownerHorseCount;
    
    mapping (uint => address payable) internal horseOwner;
    
    address payable tresorier;
    
    constructor () public {
        tresorier = msg.sender;
    }
    
    function createHorse(string memory _name, string memory _lastName) public payable returns (uint) {
        require(msg.value >= 0.1 ether, " Cout 0.1 ether");
        uint _id = ((uint) (blockhash(block.number-1))) % 2000000;
        require(!horseExist(_id), "Id produit déjà existant");
        
        uint position = horses.push(Horse({ owner : msg.sender,
                                            name : _name,
                                            lastName : _lastName,
                                            id : _id,
                                            win : 0,
                                            loose : 0 }));
                            
        ownerHorseCount[msg.sender]++;
        horseOwner[position] = msg.sender;
        
        return position;
    }
    
    function horseExist(uint _id) private view returns (bool){
        uint nbrHorses = horses.length;
        for (uint i = 0; i < nbrHorses; i++){
            if (horses[i].id == _id) return true;
        }
    }
    
}


contract Hippodrome is RaceHorses {
    
    struct Race {
        address promoter;
        uint time;
        Etat etat;
        uint bank;
        uint firstHorse;
        address[] gamblers;
        uint[] horsesParticipates;
        mapping(uint => uint) horseStats;
        mapping(uint => uint) horseOdds;
        mapping(address => uint) playerBetOn;
        mapping(address => uint) playerShoots;
        mapping(address => bool) winners;
        
    }
    
    enum Etat {
        OPEN,
        RUN,
        LOAD,
        END
    }
    
    Race[] races;
    
    event Create(uint race, address promoter);
    event Participate(uint race, uint horse);
    event Launch(uint race);
    event LeaderHorse(uint _race, uint _horse);
    
    modifier raceExist(uint _race) {
        require(races.length > _race);
        _;
    }
    
    function createRace() public payable {
        require(msg.value > 1.01 ether, "Caution de 1 ether + 0.01 de coût = 1.01");
        
        uint indiceRace = races.push(Race({promoter : msg.sender,
                                time : block.number,
                                bank : 0.01 ether,
                                etat : Etat.OPEN,
                                horsesParticipates : new uint[](0),
                                gamblers : new address[](0),
                                firstHorse : 0 }));
        emit Create(indiceRace, msg.sender);
    }
    
    function runner(uint _horse, uint _race) public payable raceExist(_race) {
        require(msg.value >= 0.01 ether, "Mettre son cheval en course coûte 0.01 ether");
        require(races[_race].etat == Etat.OPEN, "La course n'est plus ouverte");
        require(horses[_horse].owner == msg.sender, "Vous n'êtes pas le propriétaire du cheval");
        require(!alreadyRunner(msg.sender, _race), "Vous participez déjà à la course");
        
        races[_race].horsesParticipates.push(_horse);
        races[_race].bank += msg.value;
        horses[_horse].loose++;
        
        emit Participate(_race, _horse);
        
    }
    
    function alreadyRunner(address _owner, uint _race) private view returns (bool){
        
        uint nbrParticipants = races[_race].horsesParticipates.length;
        for (uint i = 0; i < nbrParticipants ; i++ ){
            if  (horseOwner[races[_race].horsesParticipates[i]] == _owner) return true;
        }
    }
    
    function gambler(uint _race, uint _horse) public payable raceExist(_race){
        require(races[_race].etat == Etat.OPEN, "La course n'est plus ouverte");
        require(runnerExist(_race, _horse), "Le cheval n'est pas inscript à la course pour le moment");
        require(msg.value >= 0.01 ether, "0.01 ether la place");
        
        races[_race].bank += msg.value;
        races[_race].gamblers.push(msg.sender);
        races[_race].horseOdds[_horse]++;
        races[_race].playerBetOn[msg.sender] = _horse;
        
    }
    
    function runnerExist(uint _race, uint _horse) private view returns (bool){
        uint nbrParticipants = races[_race].horsesParticipates.length;
        for (uint i = 0; i < nbrParticipants ; i++ ){
            if  (races[_race].horsesParticipates[i] == _horse) return true;
        }
    }
    
    function raceLaunch(uint _race) public raceExist(_race) {
        require(races[_race].promoter == msg.sender, "Vous n'êtes pas l'organisateur de cette course");
        require(races[_race].etat == Etat.OPEN, "La course n'est déjà plus ouverte");
        require(races[_race].horsesParticipates.length >= 2, "il n'y a pas assez de participants");
        
        races[_race].etat = Etat.RUN;
        races[_race].time = block.number + 22;
        emit Launch(_race);
        
    }
    
    function load(uint _race) public payable raceExist(_race) {
        require(msg.value > 0.01 ether, "0.01 ether la charge");
        require(races[_race].etat == Etat.RUN," La course est terminé");
        require(gamblerExist(_race, msg.sender), "Vous n'êtes pas inscrit à la course");
        
        races[_race].bank += msg.value;
        races[_race].playerShoots[msg.sender] = block.number + 1;
        
    }
    
    function shoot(uint _race) public raceExist(_race) {
        require(races[_race].etat == Etat.RUN, "La course est terminée");
        require(races[_race].time > block.number, "La course est terminé");
        require(gamblerExist(_race, msg.sender), "Vous n'êtes pas inscrit à la course");
        require(races[_race].playerShoots[msg.sender] > 0, "Vous n'avez pas de charge");
        require(races[_race].playerShoots[msg.sender] < block.number, "Veuillez attendre le prochain block");
        
        uint _horse = races[_race].playerBetOn[msg.sender];
        
        races[_race].playerShoots[msg.sender] = 0;
        uint power =  (uint(blockhash(races[_race].playerShoots[msg.sender])) + _horse) % 10;
        races[_race].horseStats[_horse] += power;
        
        if (races[_race].horseStats[_horse] > races[_race].horseStats[races[_race].firstHorse]){
            races[_race].firstHorse = _horse;
            emit LeaderHorse(_race, _horse);
        }
        
    }
    
    function gamblerExist(uint _race, address _gambler) private view raceExist(_race) returns(bool) {
        uint nbrParticipants = races[_race].gamblers.length;
        for (uint i = 0; i < nbrParticipants ; i++ ){
            if  (races[_race].gamblers[i] == _gambler) return true;
        }
    }
    
    function closeTheRace(uint _race) public raceExist(_race) {
        require(races[_race].promoter == msg.sender, "Vous n'êtes pas l'organisateur de cette course");
        require(races[_race].etat == Etat.RUN, "La course n'est pas en cours");
        require(races[_race].time < block.number, "La course n'est pas terminé");
        
        races[_race].etat = Etat.LOAD;
        
        horses[races[_race].firstHorse].win++;
        horses[races[_race].firstHorse].loose--;
        
        uint gain = races[_race].bank * 10 / 100;
        
        msg.sender.transfer(gain + 1 ether);
        tresorier.transfer(gain);
        horseOwner[races[_race].firstHorse].transfer(gain);
        
        races[_race].bank = races[_race].bank - (gain * 3);
        uint reste = races[_race].bank;
        races[_race].bank = races[_race].bank / races[_race].horseOdds[races[_race].firstHorse];
        
        reste = reste - (races[_race].bank * races[_race].horseOdds[races[_race].firstHorse]);
        if (reste > 0) horseOwner[races[_race].firstHorse].transfer(reste);
        
        races[_race].etat = Etat.END;
        
    }
    
    function jackpot(uint _race) public raceExist(_race) {
        require(races[_race].etat == Etat.END, "La course n'est pas fini");
        require(gamblerExist(_race, msg.sender), "Vous n'êtes pas inscrit à la course");
        require(races[_race].playerBetOn[msg.sender] == races[_race].firstHorse, "Vous n'avez pas misé sur le bon chval");
        require(!races[_race].winners[msg.sender], "Vous avez déjà pris vos gains");
        require(races[_race].bank > 0, "Les gains son nul");
        
        races[_race].winners[msg.sender] = true;
        msg.sender.transfer(races[_race].bank);
        
    }
}







