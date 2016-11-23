package sabone;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.event.Listener;
import cn.nukkit.utils.Config;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import me.onebone.economyapi.EconomyAPI;

public class Coupon extends PluginBase implements Listener{
	
		public Config config;
		public Config reward;
		
		@Override
   public void onEnable() {
     this.getServer().getPluginManager().registerEvents(this,this);
     getDataFolder().mkdirs();
     config=new (getDataFolder()+"config.yml",Config.YAML);
     reward=new (getDataFolder()+"reward.yml",Config.YAML);
   }
   
   @Override
   public void onDisable(){
   		config.save();
   		reward.save();
   }
   
   @Override
   public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args){
   		Player player = (Player)sender;
   		if(player.hasPermission("sabone.coupon"){
   			if(cmd.equals("쿠폰")){
   				switch(args[0]){
   					case "생성":
   					if(player.isOp()){
	//args는 boolean형을 가지고 있지 않게 떄문에 위와같이 따로 비교연산자를 달아주어야 합니다
   						if(args.length < 2){
   							player.sendMessage("[쿠폰] /쿠폰 <생성> <쿠폰 번호> <보상>");
   							return true;
   						}
   						//따로 변수를 선언하여 관리하는것이 가독성에도 도움을 줍니다
   						String coupon = args[1];
   						String money = args[2];
   						if(config.exists(coupon)){
   							player.sendMessage("[쿠폰] 이미 있는 쿠폰입니다.");
   							return true;
   						}
   						List<Integer> list = new ArrayList<Integer>();
   						reward.set(coupon,list);
   						config.set(coupon,money);
   					}
   					player.sendMessage("[쿠폰] 오직 오피만 가능한 명령어 입니다.");
   					return;
   					case "입력":
   					if(!args[1]){
   						player.sendMessage("[쿠폰] /쿠폰 <입력> <코드>");
   						return;
   					}
   					if(!config.get(args[1])){
   						player.sendMessage("[쿠폰] 그런 쿠폰 코드는 없습니다.");
   						return;
   					}
   					if(reward.get(args[1]).contains(player.getName())){
   						player.sendMessage("[쿠폰] 이미 사용한 쿠폰입니다.");
   						return;
   					}
   					List<Integer> List = new ArrayList<Integer>();
   					list.put(player.getName());
   					reward.set(args[1],player.getName());
   					player.sendMessage("[쿠폰] 쿠폰을 입력하였습니다. 쿠폰 :"+args[1]);
   					EconomyAPI.getInstance().addMoney(player,config.get(args[1]));
   					break;
   					case "삭제":
   					if(!player.isOp()){
   						player.sendMessage("[쿠폰] 오피만 가능한 명령어입니다.");
   						break;
   					}
   					if(!args[1]){
   						player.sendMessage("[쿠폰] 삭제할 코드를 입력해주세요.");
   						break;
   					}
   					config.remove(args[1]);
   					player.sendMessage("[쿠폰] 쿠폰을 삭제했습니다. 쿠폰 : "+args[1]);
   					reward.remove(args[1]);
   					break;
   					default;
   					if(!player.isOp()){
   						player.sendMessage("[쿠폰] /쿠폰 <입력> <코드>");
   						return;
   					}
   					player.sendMessage("[쿠폰] /쿠폰 <입력||생성||삭제>");
   					return;
   			}
   		}
   }
}
