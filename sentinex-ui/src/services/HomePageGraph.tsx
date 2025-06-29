export default function graph() {
    return (
        <div className="relative bg-gradient-to-b from-slate-50 to-white min-h-[360px] w-full h-auto">
            <svg width="850" height="300" viewBox="0 0 850 300" preserveAspectRatio="none"
                 xmlns="http://www.w3.org/2000/svg" fill="none">
                <g stroke="#4F46E5" stroke-width="3" fill="none">
                    <path
                        d="M0 220 L50 180 L100 190 L150 140 L200 160 L250 120 L300 150 L350 110 L400 100 L450 130 L500 90 L550 80 L600 100 L650 60 L700 90 L750 50 L800 70"
                        style={{
                            strokeDasharray: 1200,
                            strokeDashoffset: 1200,
                            animation: "draw 2s ease-out forwards",
                        }}/>
                    <circle id="end-dot" cx="800" cy="70" r="6" fill="#22C55E" style={{
                        opacity: 0,
                        animation: "fadeInDot 0.2s ease-out 1.2s forwards",
                    }}/>
                    <style>
                        {`
                            @keyframes draw {
                                to  {
                                    stroke-dashoffset: 0;
                                }
                            }
                            @keyframes fadeInDot {
                                from {opacity: 0;}
                                to {opacity: 1;}
                            }
                    `}
                    </style>
                </g>
                <text x="40" y="80" font-size="36" font-family="Segoe UI, sans-serif" fill="#111827"
                      font-weight="600">
                    Market Sentiment Analyzer
                </text>
                <text x="40" y="100" font-size="16" font-family="Segoe UI, sans-serif" fill="#6B7280">
                    Real-time trends powered by AI
                </text>
                <g transform="translate(640,180)">
                    <circle cx="0" cy="0" r="8" fill="#4F46E5"/>
                    <circle cx="15" cy="5" r="3" fill="#A5B4FC"/>
                    <circle cx="-14" cy="-6" r="3" fill="#A5B4FC"/>
                    <line x1="0" y1="0" x2="15" y2="5" stroke="#A5B4FC" stroke-width="2"/>
                    <line x1="0" y1="0" x2="-14" y2="-6" stroke="#A5B4FC" stroke-width="2"/>
                </g>
            </svg>
        </div>
    );
}