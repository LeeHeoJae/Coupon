package sabone;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.event.Listener;
import cn.nukkit.utils.Config;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.Player;
import me.onebone.economyapi.EconomyAPI;
import java.util.List;

public class Coupon extends PluginBase implements Listener{
	
		public Config config;
		public Config reward;
		
		@Override
   public void onEnable() {
     this.getServer().getPluginManager().registerEvents(this,this);
     this.getDataFolder().mkdirs();
     this.config=new Config(getDataFolder()+"config.yml",Config.YAML);
     this.reward=new Config(getDataFolder()+"reward.yml",Config.YAML);
   }
   
   @Override
   public void onDisable(){
   		config.save();
   		reward.save();
   }
   
   @Override
   public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args){
   		Player player = (Player)sender;
   		String coupon=args[1];
   		String reward=args[2];
   		List<Integer> list = new ArrayList<Integer>();
   		if(player.hasPermission("me.sabone.coupon")){
   			if(cmd.equals("쿠폰")){
   				switch(args[0]){
   					case "생성":
   					case "추가":
   					case "add":
   					case "a":
   					case "create":
   					case "c":
   					if(player.isOp()){
   						if(args.length<2){
   							player.sendMessage("[쿠폰] /쿠폰 <생성> <쿠폰 번호> <보상>");
   							return true;
   						}
   						if(config.exists(coupon)){
   							player.sendMessage("[쿠폰] 이미 있는 쿠폰입니다.");
   							return true;
   						}
   						reward.set(coupon,list);
   						config.set(coupon,reward);
   						player.sendMessage("[쿠폰] 쿠폰이 생성되었습니다.");
   					}
   					player.sendMessage("[쿠폰] 오직 오피만 가능한 명령어 입니다.");
   					return true;
   					case "입력":
   					case "enter":
   					case "e":
   					if(coupon==null || coupon.equals(" ")){
   						player.sendMessage("[쿠폰] /쿠폰 <입력> <코드>");
   						return true;
   					}
   					if(!config.get(coupon)){
   						player.sendMessage("[쿠폰] 그런 쿠폰 코드는 없습니다.");
   						return true;
   					}
   					if(reward.get(coupon).contains(player.getName())){
   						player.sendMessage("[쿠폰] 이미 사용한 쿠폰입니다.");
   						return true;
   					}
   					list.put(player.getName());
   					reward.set(coupon,player.getName());
   					player.sendMessage("[쿠폰] 쿠폰을 입력하였습니다. 쿠폰 :"+coupon);
   					EconomyAPI.getInstance().addMoney(player,config.get(coupon));
   					return true;
   					case "삭제":
   					case "delete":
   					case "d":
   					case "remove":
   					case "r":
   					if(!player.isOp()){
   						player.sendMessage("[쿠폰] 오피만 가능한 명령어입니다.");
   						return true;
   					}
   					if(coupon==null||coupon.equals(" ")){
   						player.sendMessage("[쿠폰] 삭제할 코드를 입력해주세요.");
   						return true;
   					}
   					config.remove(coupon);
   					player.sendMessage("[쿠폰] 쿠폰을 삭제했습니다. 쿠폰 : "+coupon);
   					reward.remove(coupon);
   					return true;
   					default:
   					if(!player.isOp()){
   						player.sendMessage("[쿠폰] /쿠폰 <입력> <코드>");
   						return true;
   					}
   					player.sendMessage("[쿠폰] /쿠폰 <입력(E)||생성(C)||삭제(D)>");
   					return true;
   			}
   		}
   }
 }
}