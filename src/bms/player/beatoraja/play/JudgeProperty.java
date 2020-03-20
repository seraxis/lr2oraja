package bms.player.beatoraja.play;

/**
 * 判定の設定値
 *
 * @author exch
 */
public enum JudgeProperty {

    FIVEKEYS(new int[][]{ {-20, 20}, {-50, 50}, {-100, 100}, {-150, 150}, {-150, 500} },
            new int[][]{ {-30, 30}, {-60, 60}, {-110, 110}, {-160, 160}, {-160, 500}},
            new int[][]{ {-120, 120}, {-150, 150}, {-200, 200}, {-250, 250}},
            new int[][]{ {-130, 130}, {-160, 160}, {-110, 110}, {-260, 260}},
            new boolean[]{true, true, true, false, false, false },
            MissCondition.ALWAYS,
            new boolean[]{true, true, true, true, true, false },
            JudgeWindowRule.NORMAL
            ),
    SEVENKEYS(new int[][]{ {-20, 20}, {-60, 60}, {-150, 150}, {-280, 220}, {-150, 500} },
            new int[][]{ {-30, 30}, {-70, 70}, {-160, 160}, {-290, 230}, {-160, 500}},
            new int[][]{ {-120, 120}, {-160, 160}, {-200, 200}, {-280, 220}},
            new int[][]{ {-130, 130}, {-170, 170}, {-210, 210}, {-290, 230}},
            new boolean[]{true, true, true, false, false, true },
            MissCondition.ALWAYS,
            new boolean[]{true, true, true, true, true, false },
            JudgeWindowRule.NORMAL
            ),
    PMS(new int[][]{ {-20, 20}, {-50, 50}, {-117, 117}, {-183, 183}, {-175, 500} },
            new int[][]{},
            new int[][]{ {-120, 120}, {-150, 150}, {-217, 217}, {-283, 283}},
            new int[][]{},
            new boolean[]{true, true, true, false, false, false },
            MissCondition.ONE,
            new boolean[]{true, true, true, false, true, false },
            JudgeWindowRule.PMS
            ),
	KEYBOARD(new int[][]{ {-30, 30}, {-90, 90}, {-200, 200}, {-320, 240}, {-200, 650} },
			new int[][]{},
			new int[][]{ {-160, 25}, {-200, 75}, {-260, 140}, {-320, 240}},
			new int[][]{},
            new boolean[]{true, true, true, false, false, true },
            MissCondition.ALWAYS,
            new boolean[]{true, true, true, true, true, false },
            JudgeWindowRule.NORMAL
			),
    LR2(new int[][]{{-21, 21}, {-60, 60}, {-120, 120}, {-200, 200}, {0, 1000}}, 
            new int[][]{{-21, 21}, {-60, 60}, {-120, 120}, {-200, 200}, {0, 1000}}, 
            new int[][]{{-120, 120}, {-160, 160}, {-170, 170}, {-200, 200}}, 
            new int[][]{{-120, 120}, {-160, 160}, {-170, 170}, {-200, 200}}, 
            new boolean[]{true, true, true, false, false, true },
            MissCondition.ALWAYS,
            new boolean[]{true, true, true, true, true, false },
            JudgeWindowRule.NORMAL
            ),
	;

    /**
     * 通常ノートの格判定幅。PG, GR, GD, BD, MSの順で{LATE下限, EARLY上限}のセットで表現する。
     */
    private final int[][] note;
    /**
     * スクラッチノートの格判定幅。PG, GR, GD, BD, MSの順で{LATE下限, EARLY上限}のセットで表現する。
     */
    private final int[][] scratch;
    /**
     * 通常ロングノート終端の格判定幅。PG, GR, GD, BD, MSの順で{LATE下限, EARLY上限}のセットで表現する。
     */
    private final int[][] longnote;
    /**
     * スクラッチロングノート終端の格判定幅。PG, GR, GD, BD, MSの順で{LATE下限, EARLY上限}のセットで表現する。
     */
    private final int[][] longscratch;
    /**
     * 各判定毎のコンボ継続
     */
    public final boolean[] combo;
    /**
     * MISSの発生回数
     */
    public final MissCondition miss;
    /**
     * 各判定毎のノートの判定を消失するかどうか。PG, GR, GD, BD, PR, MSの順
     */
    public final boolean[] judgeVanish;
    
    public final JudgeWindowRule windowrule;

    private JudgeProperty(int[][] note, int[][] scratch, int[][] longnote, int[][] longscratch, boolean[] combo, MissCondition miss, boolean[] judgeVanish, JudgeWindowRule windowrule) {
        this.note = note;
        this.scratch = scratch;
        this.longnote = longnote;
        this.longscratch = longscratch;
        this.combo = combo;
        this.miss = miss;
        this.judgeVanish = judgeVanish;
        this.windowrule = windowrule;
    }

    public int[][] getNoteJudge(int judgerank, int judgeWindowRate, int constraint) {
    	return windowrule.create(note, judgerank, judgeWindowRate, constraint);
    }

    public int[][] getLongNoteEndJudge(int judgerank, int judgeWindowRate, int constraint) {
    	return windowrule.create(longnote, judgerank, judgeWindowRate, constraint);
    }

    public int[][] getScratchJudge(int judgerank, int judgeWindowRate, int constraint) {
    	return windowrule.create(scratch, judgerank, judgeWindowRate, constraint);
    }

    public int[][] getLongScratchEndJudge(int judgerank, int judgeWindowRate, int constraint) {
    	return windowrule.create(longscratch, judgerank, judgeWindowRate, constraint);
    }
    
    public enum MissCondition {
    	ONE, ALWAYS
    }
    
    public enum JudgeWindowRule {
    	NORMAL (new int[]{25, 50, 75, 100, 75}){

			@Override
			public int[][] create(int[][] org, int judgerank, int judgeWindowRate, int constraint) {
				return JudgeWindowRule.create(org, judgerank,judgeWindowRate, constraint, false);
			}
    		
    	},
    	PMS (new int[]{25, 50, 75, 100, 75}) {

			@Override
			public int[][] create(int[][] org, int judgerank, int judgeWindowRate, int constraint) {
				return JudgeWindowRule.create(org, judgerank,judgeWindowRate, constraint, true);
			}
    		
    	};
    	
    	/**
    	 * JUDGERANKの倍率(VERYHARD, HARD, NORMAL, EASY, VERYEASY)
    	 */
    	public final int[] judgerank;

        private static final int[][] LR2_JUDGE_WINDOWS = {
            {0,8,15,18,21}, // PGREAT
            {0,24,30,40,60}, // GREAT
            {0,40,60,100,120}, // GOOD
        };

    	
        private static int[][] create(int[][] org, int judgerank, int judgeWindowRate, int constraint, boolean pms) {
    		final int[][] judge = new int[org.length][2];
            for (int i = 0; i < judge.length; i++) {
                for(int j = 0; j < judge[i].length; j++) {
                    judge[i][j] = org[i][j];
                }
            }
    		//final boolean[] fix = pms ? new boolean[]{true, false, false, true, true} : new boolean[]{false, false, false, false, true};
    		//for (int i = 0; i < judge.length; i++) {
    		//	for(int j = 0;j < 2;j++) {
			//		judge[i][j] = fix[i] ? org[i][j] : org[i][j] * judgerank / 100;
    		//	}
    		//}

            // Interpolate LR2 Judge windows
            {
                // only change pgreat, great, good
                final int fixmax = 3;
                if (judgerank < 100) {
                    int judgeindex = judgerank/25;
                    int interpolate = judgerank%25;
                    for (int i = 0; i < fixmax; i++) {
                        int[] lr2judge = LR2_JUDGE_WINDOWS[i];
                        for(int j = 0; j < 2; j++) {
                            int interpolatedJudge = lr2judge[judgeindex] + (interpolate*(lr2judge[judgeindex+1] - lr2judge[judgeindex]) + 12)/25;
                            judge[i][j] = org[i][j] * interpolatedJudge / lr2judge[4];
                        }
                    }
                } else {
                    for (int i = 0; i < fixmax; i++) {
                        for(int j = 0; j < 2; j++) {
                            judge[i][j] = org[i][j] * judgerank / 100;
                        }
                    }
                }

                // correction if we exceed the bad windows
                for (int i = fixmax-1; i >= 0; i--) {
                    for(int j = 0; j < 2; j++) {
                        if(Math.abs(judge[i][j]) > Math.abs(judge[i+1][j])) {
                            judge[i][j] = judge[i+1][j];
                        }
                    }
                }
            }

    		// judgeWindowRateによる補正
    		for (int i = 0; i < Math.min(org.length, 2); i++) {
    			for(int j = 0;j < 2;j++) {
					judge[i][j] = judge[i][j]*judgeWindowRate / 100;
					if(Math.abs(judge[i][j]) > Math.abs(judge[2][j])) {
						judge[i][j] = judge[2][j];
					}
    			}
    		}
    		
    		// constraintによる判定補正
    		for (int i = constraint; i < Math.min(org.length - 1, 2); i++) {
    			for(int j = 0;j < 2;j++) {
					judge[i + 1][j] = judge[i][j];    				
    			}
    		}
    		
    		return judge;
        }
        
        private JudgeWindowRule(int[] judgerank) {
        	this.judgerank = judgerank;
        }
        
    	public abstract int[][] create(int[][] org, int judgerank, int judgeWindowRate, int constraint);
    }
}
