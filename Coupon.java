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
   						if(!args[1]||!args[2]){
   							player.sendMessage("[쿠폰] /쿠폰 <생성> <쿠폰 번호> <보상>");
   							return;
   						}
   						if(config.get(args[1]).equals(null)){
   							player.sendMessage("[쿠폰] 이미 있는 쿠폰입니다.");
   							return;
   						}
   						List<Integer> list = new ArrayList<Integer>()
   						list.add(sender->getName());
   						reward.set(args[1],list);
   						config.set(args[1],args[2]);
   						player.sendMessage("[쿠폰] 쿠폰이 생성되었습니다.");
   						break;
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